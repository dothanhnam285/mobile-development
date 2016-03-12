- Open project in Netbean and deploy with Glassfish
- USAGE:
	+ To see list of supported currencis: localhost:8080/money/currencies
	+ To convert money: localhost:8080/money/convert?from={baseList}&amount={value}&to={symbolList}
		. {baseList}: base currencies to convert from. Ex: USD,EUR
		. {value}: the amount of money to convert.
		. {symbolList}: currencies to convert to. Ex: VND,JPY
		Ex: localhost:8080/money/convert?from=USD,EUR&amount=10&to=VND,JPY,CNY