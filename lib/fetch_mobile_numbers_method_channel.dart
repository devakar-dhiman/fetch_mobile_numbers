import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'fetch_mobile_numbers_platform_interface.dart';

/// An implementation of [FetchMobileNumbersPlatform] that uses method channels.
class MethodChannelFetchMobileNumbers extends FetchMobileNumbersPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('fetch_mobile_numbers');

  @override
  Future<List?> getMobileNumbers() async {
    List? version = await methodChannel.invokeMethod('getMobileNumbers');
    return version;
  }
}
