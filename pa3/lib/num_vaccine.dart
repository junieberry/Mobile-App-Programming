import 'package:pa3/parse_vaccine.dart';

class VaccineNum {
  List<World> worlds;

  String ParsedLatestDate;
  int TotalVacc = 0;
  int TotalFullyVacc = 0;
  int DailyVacc = 0;

  VaccineNum(List<World> worlds) {
    this.worlds = worlds;
    GetParsedLatestDate();

    for (var i in worlds) {
      var recent = i.data[i.data.length - 1];
      for (var j in i.data.reversed) {
        if (j.date == ParsedLatestDate) {
          ///2. Total vacc
          if (j.total_vaccinations != null) {
            TotalVacc += j.total_vaccinations;
          } else if (j.people_vaccinated != null) {
            TotalVacc += j.people_vaccinated;
          } else if (j.people_fully_vaccinated != null) {
            TotalVacc += j.people_fully_vaccinated;
          }

          ///3. Total fully Vacc
          if (j.people_fully_vaccinated != null) {
            TotalFullyVacc += j.people_fully_vaccinated;
          } else {
            var before = i.data[i.data.indexOf(j) - 1];
            var d = DateTime.parse(ParsedLatestDate);
            if (DateTime.parse(before.date) ==
                DateTime(d.year, d.month, d.day - 1)) {
              if (before.people_fully_vaccinated != null) {
                TotalFullyVacc += before.people_fully_vaccinated;
              }
            }
          }

          ///4. Daily Vacc
          if (j.daily_vaccinations != null) {
            DailyVacc += j.daily_vaccinations;
          } else {
            var before = i.data[i.data.indexOf(j) - 1];
            var d = DateTime.parse(ParsedLatestDate);
            if (DateTime.parse(before.date) ==
                DateTime(d.year, d.month, d.day - 1)) {
              if (before.daily_vaccinations != null) {
                TotalFullyVacc += before.daily_vaccinations;
              }
            }
          }

          break;
        } else if (DateTime.parse(j.date)
            .isBefore(DateTime.parse(ParsedLatestDate))) {
          ///2. Total vacc
          if (recent.total_vaccinations != null) {
            TotalVacc += recent.total_vaccinations;
          } else if (recent.people_vaccinated != null) {
            TotalVacc += recent.people_vaccinated;
          } else if (recent.people_fully_vaccinated != null) {
            TotalVacc += recent.people_fully_vaccinated;
          }

          ///3. Total fully Vacc
          if (recent.people_fully_vaccinated != null) {
            TotalFullyVacc += recent.people_fully_vaccinated;
          }

          ///4. Daily Vcc
          if (recent.daily_vaccinations != null) {
            DailyVacc += recent.daily_vaccinations;
          }

          break;
        }
      }
    }
  }

  void GetParsedLatestDate() {
    for (var i in worlds) {
      if (i.country == "South Korea") {
        this.ParsedLatestDate = i.data[i.data.length - 1].date;
      }
    }
  }
}
