set -e;

clientdata_path="/clientdata/x";
server_hostname=$SERVER_HOSTNAME;
port=$PORT;

data="$(nc $server_hostname $port)";
checksum_received="$(echo $data | cut -d'|' -f1)";
printf "$data" | awk '{split($0,a,"|"); printf a[2]}' > "$clientdata_path";
checksum_calculated="$(md5sum "$clientdata_path" | cut -d' ' -f1)";

if [ "$checksum_calculated" == "$checksum_received" ]
then
	echo "Success!";
else
	echo "Failed!";
fi;
