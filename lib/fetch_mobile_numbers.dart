
import 'sim_card/sim_card_info.dart';
import 'fetch_mobile_numbers_platform_interface.dart';

class FetchMobileNumbers {

  List<SimCard> simList = [];

  Future<List<SimCard>> getMobileNumbers() async {
    List<dynamic> sims =
        (await FetchMobileNumbersPlatform.instance.getMobileNumbers()) ?? [];

    if (sims.isNotEmpty) {
      List<Map<String, dynamic>> mapList = sims.map((obj) {
        return {
          'slotIndex': obj["slotIndex"] ?? '',
          'carrierName': obj["carrierName"] ?? '',
          'countryISO': obj["countryISO"] ?? '',
          'number': obj["number"] ?? '',
        };
      }).toList();

      for (var element in mapList) {
        simList.add(SimCard.fromJson(element));
      }
    }
    return simList;
  }
}