set -e;

clientdata_path="/tmp/clientdata";
port=9999;

data="$(nc localhost $port)";
echo $data;
checksum_received="$(echo $data | cut -d'|' -f1)";
echo $checksum_received;
echo "$data" | cut -d'|' -f2- > "$clientdata_path";
checksum_calculated="$(md5 "$clientdata_path" | cut -d' ' -f1)";

if [ "$checksum_calculated" == "$checksum_received" ]
then
	echo "Success!";
else
	echo "Failed!";
fi;
