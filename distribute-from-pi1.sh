git pull
./build-with.dependencies.sh
sshpass -p raspberry scp target/*.jar pi@pi2:~/2dcloudjar
sshpass -p raspberry scp target/*.jar pi@pi3:~/2dcloudjar
sshpass -p raspberry scp target/*.jar pi@pi4:~/2dcloudjar
cp target/*.jar ~/2dcloudjar
