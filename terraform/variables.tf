variable vpc-cidr-block {
    default = "10.0.0.0/16"
}
variable subnet-cidr-block {
    default = "10.0.1.0/24"
}
variable environment {
    default = "dev"
}
variable avail-zone {
    dddefault = "ap-south-1a"
}
variable myip {
    default = "172.16.20.20/32s"
}
variable instance_type {
    default = "t2.medium"
}
variable region {
    default = "ap-south-1"
  
}
/*variable pub-key-location {}
variable private-key-location {}*/