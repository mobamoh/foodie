gcloud auth login

docker tag com.mohamedbamoh.foodie/order.service:$1 europe-west4-docker.pkg.dev/foodie-346904/foodie-repository/order.service:$1
docker tag com.mohamedbamoh.foodie/payment.service:$1 europe-west4-docker.pkg.dev/foodie-346904/foodie-repository/payment.service:$1
docker tag com.mohamedbamoh.foodie/restaurant.service:$1 europe-west4-docker.pkg.dev/foodie-346904/foodie-repository/restaurant.service:$1
docker tag com.mohamedbamoh.foodie/customer.service:$1 europe-west4-docker.pkg.dev/foodie-346904/foodie-repository/customer.service:$1

docker push europe-west4-docker.pkg.dev/foodie-346904/foodie-repository/order.service:$1
docker push europe-west4-docker.pkg.dev/foodie-346904/foodie-repository/payment.service:$1
docker push europe-west4-docker.pkg.dev/foodie-346904/foodie-repository/restaurant.service:$1
docker push europe-west4-docker.pkg.dev/foodie-346904/foodie-repository/customer.service:$1

