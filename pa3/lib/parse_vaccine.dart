import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class Data {
  String date;
  int total_vaccinations;
  int daily_vaccinations;
  int people_vaccinated;
  int people_fully_vaccinated;

  Data({
    this.date,
    this.total_vaccinations,
    this.daily_vaccinations,
    this.people_vaccinated,
    this.people_fully_vaccinated,
  });

  factory Data.fromJson(Map<String, dynamic> json) {
    return Data(
      date: json['date'] as String,
      total_vaccinations: json['total_vaccination'] as int,
      daily_vaccinations: json['daily_vaccinations'] as int,
      people_fully_vaccinated: json['people_fully_vaccinated'] as int,
      people_vaccinated: json['people_vaccinated'] as int,
    );
  }
}

class World {
  String country;
  String iso_code;
  List<Data> data;

  World({
    this.country,
    this.iso_code,
    this.data,
  });

  factory World.fromJson(Map<String, dynamic> json) {
    return World(
      country: json['country'] as String,
      iso_code: json['iso_code'] as String,
      data: json['data'].map<Data>((json) => Data.fromJson(json)).toList(),
    );
  }
}

Future<List<World>> FetchVaccineData(http.Client client) async {
  final response = await client.get(Uri.parse(
      "https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/vaccinations/vaccinations.json"));
  return compute(ParseVaccineData, response.body);
}

List<World> ParseVaccineData(String responseBody) {
  final parsed = json.decode(responseBody).cast<Map<String, dynamic>>();
  return parsed.map<World>((json) => World.fromJson(json)).toList();
}
