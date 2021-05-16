import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class Subway {
  final int rowNum;
  final String subwayId;
  final String trainLineNm;
  final String subwayHeading;
  final String arvlMsg2;

  Subway(
      {this.rowNum,
      this.subwayId,
      this.trainLineNm,
      this.subwayHeading,
      this.arvlMsg2});

  factory Subway.fromJson(Map<String, dynamic> json) {
    print(json['realtimeArrivalList']);
    return Subway(
      rowNum: json['realtimeArrivalList'][0]['rowNum'],
      subwayId: json['realtimeArrivalList'][0]['subwayId'],
      trainLineNm: json['realtimeArrivalList'][0]['trainLineNm'],
      subwayHeading: json['realtimeArrivalList'][0]['subwayHeading'],
      arvlMsg2: json['realtimeArrivalList'][0]['arvlMsg2'],
    );
  }
}

Future<Subway> fetchSubway() async {
  final response = await http.get(
      "http://swopenAPI.seoul.go.kr/api/subway/sample/json/realtimeStationArrival/0/5/성균관대");
  if (response.statusCode == 200) {
    return Subway.fromJson(jsonDecode(response.body));
  } else {
    throw Exception('Failed to load Subway');
  }
}

void main() async {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Future<Subway> futureSubway;

  @override
  void initState() {
    super.initState();
    futureSubway = fetchSubway();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Fetch Data Example'),
        ),
        body: Center(
            child: FutureBuilder<Subway>(
                future: futureSubway,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Container(
                      child: Column(
                        children: [
                          Text(snapshot.data.rowNum.toString()),
                          Text(snapshot.data.subwayId.toString()),
                          Text(snapshot.data.trainLineNm.toString()),
                          Text(snapshot.data.subwayHeading.toString()),
                          Text(snapshot.data.arvlMsg2.toString()),
                        ],
                      ),
                    );
                  } else if (snapshot.hasError) {
                    return Text("${snapshot.error}");
                  }
                  return CircularProgressIndicator();
                })));
  }
}
