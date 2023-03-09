docker-compose up -d
./init.sh
mvn clean package
java -jar pizza-shop-repository/target/*.jar &
java -jar pizza-shop-web-receiver/target/*.jar &
java -jar pizza-shop-transformer/target/*.jar &