{:dev  {:env {:kafka   "gabrielgio.com.br:9092"
              :datomic "datomic:sql://main?jdbc:mysql://gabrielgio.com.br:3306/datomic?user=remote&password=remote"
              :mongo   "mongodb://remote:remote@gabrielgio.com.br:27017/main"}}
 :prod {:env {:kafka   "gabrielgio.com.br:9092"
              :datomic "datomic:sql://main?jdbc:mysql://gabrielgio.com.br:3306/datomic?user=remote&password=remote"
              :mongo   "mongodb://remote:remote@gabrielgio.com.br:27017/main"}}}