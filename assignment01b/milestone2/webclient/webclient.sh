set -e;

clientdata_path="/clientdata";
port=$PORT;

while true; do
    checksum="$(ls $clientdata_path)";
    echo '{
    "checksum" : "'"$checksum"'",
    "data" : "'"$(cat $clientdata_path/$checksum)"'"
}' | nc -l -p $port;

done;