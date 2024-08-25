import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'fetch_mobile_numbers_method_channel.dart';

abstract class FetchMobileNumbersPlatform extends PlatformInterface {
  /// Constructs a FetchMobileNumbersPlatform.
  FetchMobileNumbersPlatform() : super(token: _token);

  static final Object _token = Object();

  static FetchMobileNumbersPlatform _instance = MethodChannelFetchMobileNumbers();

  /// The default instance of [FetchMobileNumbersPlatform] to use.
  ///
  /// Defaults to [MethodChannelFetchMobileNumbers].
  static FetchMobileNumbersPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FetchMobileNumbersPlatform] when
  /// they register themselves.
  static set instance(FetchMobileNumbersPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<List?> getMobileNumbers() {
    throw UnimplementedError('MobileNumber() has not been implemented.');
  }
}
