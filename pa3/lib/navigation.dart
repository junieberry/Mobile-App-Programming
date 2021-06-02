import 'package:flutter/material.dart';

class Navigation extends StatelessWidget {
  final Map<String, String> arguments;
  Navigation(this.arguments);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Menu')),
      body: Center(
        child: Column(children: [
          Row(
            children: [
            ],
          )

        ],),
      )),
    );
  }
}
