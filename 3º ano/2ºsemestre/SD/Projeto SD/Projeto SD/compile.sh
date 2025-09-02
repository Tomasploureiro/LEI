rm -rf target/search
mkdir -p target/search

javac -d target -cp target/lib/jsoup-1.18.3.jar src/main/java/search/*.java

