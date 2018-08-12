set -e;

temp_data_path="/tmp/data"
config_map="/tmp/configMap.yml";
server_hostname=$SERVER_HOSTNAME;
port=$PORT;

data="$(timeout -t 1 nc $server_hostname $port)";
checksum_received="$(echo $data | cut -d'|' -f1)";
printf "$data" | awk '{split($0,a,"|"); printf a[2]}' > "$temp_data_path";
checksum_calculated="$(md5sum "$temp_data_path" | cut -d' ' -f1)";

if [ "$checksum_calculated" == "$checksum_received" ]
then
	cp "/client/partialConfigMap.yml" "$config_map";
	echo "$checksum_calculated: $(cat $temp_data_path)" >> "$config_map";
	kubectl apply -f $config_map --namespace cnu2018-dev;
	echo "Success!";
else
	echo "Failed!";
fi;
