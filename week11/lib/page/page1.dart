import 'package:flutter/material.dart';

class Page1 extends StatelessWidget {
  final Map<String, String> arguments;
  Page1(this.arguments);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title:Text("Page 1"),
      ),
      body : Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(arguments["user-msg1"]),
            Text(arguments["user-msg2"]),
          ],
        ),
      )
    );
  }
}
