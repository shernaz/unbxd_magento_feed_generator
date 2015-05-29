# unbxd_magento_feed_generator

To call:
```
java -cp out/artifacts/unbxd_magento_feed_generator_jar/unbxd_magento_feed_generator.jar com.unbxd.App --sizeurl "http://magento-sandbox.cloudapp.net/magento/index.php/searchcore/catalog/size?site=Main%20Website&auth=aHR0cDovL21hZ2VudG8tc2FuZGJveC5jbG91ZGFwcC5uZXQvbW&limit=1" --per-thread 500 --site-name "alb1n-u1416301593789" --secret-key "334046b701a96e2a117483d120d924d7"
OR
java -cp out/artifacts/unbxd_magento_feed_generator_jar/unbxd_magento_feed_generator.jar com.unbxd.App --sizeurl "http://bookpal03.gemshelp.com/index.php/searchcore/catalog/size?site=Main%20Website&auth=653d0e345a01c543bf75a8897b6ff16d" --per-thread 500 --site-name "bookpal_com-u1430289925220" --secret-key "653d0e345a01c543bf75a8897b6ff16d" --username "bookpal" --password "KuPCPzx3gO"


time java -cp out/artifacts/unbxd_magento_feed_generator_jar/unbxd_magento_feed_generator.jar com.unbxd.App --sizeurl "http://unbxd.tolexo.com/index.php/recscore/catalog/size?site=Main%20Website&auth=9b24109f3db3ef5c1c1c2fe81a7b4642&key=auto" --per-thread 500 --site-name "tolexo-u1429108687932" --secret-key "9b24109f3db3ef5c1c1c2fe81a7b4642" --no-flush
```
