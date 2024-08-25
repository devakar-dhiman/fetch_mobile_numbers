class SimCard {
  final String slotIndex;
  final String carrierName;
  final String countryISO;
  final String number;

  SimCard({
    required this.slotIndex,
    required this.carrierName,
    required this.countryISO,
    required this.number,
  });

  // Convert a SimCard instance into a map
  Map<String, dynamic> toJson() {
    return {
      'slotIndex': slotIndex,
      'carrierName': carrierName,
      'countryISO': countryISO,
      'number': number,
    };
  }

  // Convert a map into a SimCard instance
  factory SimCard.fromJson(Map<String, dynamic> json) {
    return SimCard(
      slotIndex: json['slotIndex'],
      carrierName: json['carrierName'],
      countryISO: json['countryISO'],
      number: json['number'],
    );
  }

  @override
  String toString() {
    return 'slotIndex: $slotIndex,\ncarrierName: $carrierName,\ncountryISO: $countryISO,\nnumber: $number\n';
  }
}
