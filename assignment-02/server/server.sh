set -e;

serverdata_path="/tmp/serverdata";
port=9999;

LC_ALL=C cat /dev/urandom | tr -dc 'a-zA-Z0-9' | dd of="$serverdata_path" bs=1024 count=1;
checksum="$(md5 "$serverdata_path" | cut -d' ' -f1)";

echo "$checksum|$(cat $serverdata_path)" | nc -l $port;
