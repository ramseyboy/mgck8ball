# magic8ball

just toying around with Clojure / Postgres / Lucene / OpenNLP

###dependencies
```
lein deps
```

### to start
```
cd api
./create-db.sh # just uses a sqlite3 db for now
lein ring server-headless
```

### usage
```
curl -G "http://localhost:3000/ask/" --data-urlencode "question=should I invest in gold?"

# or just visit
http://localhost:3000/ask?question=should I invest in gold?"
```

```
# to see all questions asked
curl -G "http://localhost:3000/questions/"

# to see all answers
curl -G "http://localhost:3000/answers/"
```
