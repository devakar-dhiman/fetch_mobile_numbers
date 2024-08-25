import 'package:flutter/material.dart';
import 'dart:async';
import 'package:fetch_mobile_numbers/sim_card/sim_card_info.dart';
import 'package:flutter/services.dart';
import 'package:fetch_mobile_numbers/fetch_mobile_numbers.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List<SimCard> _mobileNumbers = [];
  final _fetchMobileNumbersPlugin = FetchMobileNumbers();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    List<SimCard>? mobileNumbers;
    try {
      mobileNumbers =
          await _fetchMobileNumbersPlugin.getMobileNumbers();
    } on PlatformException {
      mobileNumbers = [];
    }

    if (!mounted) return;

    setState(() {
      _mobileNumbers = mobileNumbers ?? [];
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              if(_mobileNumbers.isNotEmpty && _mobileNumbers.length == 1)
              Text('${_mobileNumbers[0].toString()}\n'),
              if(_mobileNumbers.isNotEmpty && _mobileNumbers.length == 2)
                Text('${_mobileNumbers[0].toString()}\n'),
              if(_mobileNumbers.isNotEmpty && _mobileNumbers.length == 2)
                Text('${_mobileNumbers[1].toString()}\n'),
              if(_mobileNumbers.isEmpty)
                const Text('Unble to Detect Sim Numbers\n'),
            ],
          ),
        ),
      ),
    );
  }
}
