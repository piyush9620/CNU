set -e;

serverdata_path="/serverdata/x";
port=9999;

export LC_ALL=C;
cat /dev/urandom | tr -dc 'a-zA-Z0-9' | dd of="$serverdata_path" bs=1024 count=1;
checksum="$(md5sum "$serverdata_path" | cut -d' ' -f1)";

printf "$checksum|$(cat $serverdata_path)" | nc -l $port;
