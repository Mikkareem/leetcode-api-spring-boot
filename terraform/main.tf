provider "aws" {
  region = "ap-south-1"  # Change the region as needed
}

resource "aws_instance" "lotcode_ec2" {
  ami           = "ami-04a37924ffe27da53" # Amazon Linux 2 AMI in us-east-1, update based on your region
  instance_type = "t2.micro"
  key_name      = "TechullurgyTestServerKeyPair" # Replace with your key pair name

  # Security group to allow SSH and HTTP access
  vpc_security_group_ids = [aws_security_group.lotcode_sg.id]
  subnet_id = aws_subnet.lotcode_subnet.id

  # Provisioner for uploading
  provisioner "file" {
    source      = "${path.module}/leetcode-clone-2-0.0.1.jar"  # Path to your JAR file on your local machine
    destination = "/home/ec2-user/leetcode-app.jar"

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("~/DEV/subordinates/SSHKeys/TechullurgyTestServerKeyPair.pem")  # Path to your private key
      host        = self.public_ip
    }
  }

  provisioner "remote-exec" {
    inline = [
      "sudo yum update -y",
      "sudo yum install -y docker",
      "sudo service docker start",
      "sudo usermod -a -G docker ec2-user",

      "sudo docker pull node:latest",
      "sudo docker pull gcc:latest",
      "sudo docker pull amazoncorretto:latest",

      "sudo yum install -y java-17-amazon-corretto",
      "sudo nohup java -jar leetcode-app.jar &",
      "sleep 15"
    ]

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("~/DEV/subordinates/SSHKeys/TechullurgyTestServerKeyPair.pem")  # Path to your private key
      host        = self.public_ip
    }
  }

  tags = {
    Name = "Lotcode-EC2-Instance"
  }
}

resource "aws_vpc" "lotcode_vpc" {
  cidr_block = "10.0.0.0/24"
  tags = {
    Name = "LotcodeVpc01"
  }
}

resource "aws_internet_gateway" "lotcode_igw" {
  vpc_id = aws_vpc.lotcode_vpc.id

  tags = {
    Name = "Lotcode-Internet_Gateway"
  }
}

resource "aws_subnet" "lotcode_subnet" {
  vpc_id     = aws_vpc.lotcode_vpc.id
  cidr_block = "10.0.0.1/24"
  availability_zone = "ap-south-1a"
  map_public_ip_on_launch = true

  tags = {
    Name = "Lotcode-Public-Subnet"
  }
}

resource "aws_route_table" "lotcode_rt" {
  vpc_id = aws_vpc.lotcode_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.lotcode_igw.id
  }

  tags = {
    Name = "Lotcode-Route-Table"
  }
}

resource "aws_route_table_association" "lotcode_rta" {
  route_table_id = aws_route_table.lotcode_rt.id
  subnet_id = aws_subnet.lotcode_subnet.id
}

# Security group allowing SSH and HTTP
resource "aws_security_group" "lotcode_sg" {
  name        = "lotcode-sg"
  description = "Allow SSH and HTTP traffic"
  vpc_id = aws_vpc.lotcode_vpc.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Output instance public IP
output "instance_public_ip" {
  value = aws_instance.lotcode_ec2.public_ip
}