# Fetch Mobile Numbers
A Flutter package to fetch mobile numbers and SIM card information from a device. This package uses platform-specific code to access SIM card details, providing an easy way to retrieve this information within your Flutter applications.

## Features
Retrieve mobile numbers and SIM card details.
Supports multiple SIM cards.
Provides information such as slot index, carrier name, country ISO, and phone number.
##Installation
To use this package, add fetch_mobile_numbers as a dependency in your pubspec.yaml file:

```yaml
dependencies:
fetch_mobile_numbers: ^0.0.1
```
Then, run flutter pub get to install the package.

## Usage
### Import the Package
```dart
import 'package:fetch_mobile_numbers/fetch_mobile_numbers.dart';
```
### Fetch Mobile Numbers
To retrieve mobile numbers and SIM card details, use the FetchMobileNumbers class:

```dart
import 'package:fetch_mobile_numbers/fetch_mobile_numbers.dart';

void main() async {
final fetchMobileNumbers = FetchMobileNumbers();

// Fetch mobile numbers
List<SimCard>? simCards = await fetchMobileNumbers.getMobileNumbers();

// Check if the list is not null and not empty
if (simCards != null && simCards.isNotEmpty) {
    for (var simCard in simCards) {
    print(simCard);
  }
} else {
    print('No SIM cards found.');
}

```

## Classes
### 'FetchMobileNumbers'
The main class to interact with the SIM card information.

### Methods
* Future<List<SimCard>?> getMobileNumbers()

Fetches a list of SimCard instances representing the SIM card details available on the device.

### 'SimCard'
A class representing the information of a SIM card.

### Properties
* String slotIndex - The slot index of the SIM card.
* String carrierName - The carrier name of the SIM card.
* String countryISO - The ISO code of the country.
* String number - The phone number associated with the SIM card.
### Methods
* Map<String, dynamic> toJson()

Converts the SimCard instance into a map for JSON serialization.

* factory SimCard.fromJson(Map<String, dynamic> json)

Creates a SimCard instance from a map.

* @override String toString()

Provides a string representation of the SimCard instance.

## Platform Support
This package currently supports the following platforms:

* Android
  Please note that support for iOS or other platforms is not yet implemented.

## Contributing
If you would like to contribute to this project, please fork the repository and create a pull request with your changes. Ensure that you follow the coding style and write tests for your modifications.

## License
This package is licensed under the MIT License. See the LICENSE file for more information.

## Contact
For any questions or issues, please open an issue on the GitHub repository or contact the maintainer.
