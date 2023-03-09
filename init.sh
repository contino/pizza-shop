echo "Re-creating Kafka topics ..."

echo "Deleting old topics..."

docker exec kafka \
kafka-topics --bootstrap-server kafka:9092 \
             --delete \
             --if-exists \
             --topic inbound-messages

docker exec kafka \
kafka-topics --bootstrap-server kafka:9092 \
             --delete \
             --if-exists \
             --topic outbound-messages

echo "Creating topics..."

docker exec kafka \
kafka-topics --bootstrap-server kafka:9092 \
             --create \
             --topic inbound-messages

docker exec kafka \
kafka-topics --bootstrap-server kafka:9092 \
             --create \
             --topic outbound-messages