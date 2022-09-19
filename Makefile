run: PayCalculatorAppDriver.class BAH.class BAS.class BasePay.class Data.class Grade.class HashTableMap.class Locality.class MapADT.class SpecialPay.class
	java PayCalculatorAppDriver
clean:
	rm *.class
test: TestCalculator.class PayCalculatorAppDriver.class BAH.class BAS.class BasePay.class Data.class Grade.class HashTableMap.class Locality.class MapADT.class SpecialPay.class
	java -jar junit5.jar -cp . --scan-classpath
# .class dependencies below
PayCalculatorAppDriver.class: PayCalculatorAppDriver.java
	javac PayCalculatorAppDriver.java
BAH.class: BAH.java
	javac BAH.java
BAS.class: BAS.java
	javac BAS.java
BasePay.class: BasePay.java
	javac BasePay.java
Data.class: Data.java
	javac Data.java
Grade.class: Grade.java
	javac Grade.java
HashTableMap.class: HashTableMap.java
	javac HashTableMap.java
Locality.class: Locality.java
	javac Locality.java
MapADT.class: MapADT.java
	javac MapADT.java
SpecialPay.class: SpecialPay.java
	javac SpecialPay.java
TestCalculator.class: TestCalculator.java
	javac -cp .:junit5.jar TestCalculator.java
