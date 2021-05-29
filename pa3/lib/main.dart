import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  int login = 0;
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: LoginPage(login: login),
    );
  }
}

class LoginPage extends StatelessWidget {
  LoginPage({Key key, this.login}) : super(key: key);

  int login;

  final _idController = TextEditingController();
  final _pwController = TextEditingController();

  void _login() {
    if ((_idController.text == "skku") && (_pwController.text == "1234")) {
      login = 1;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("2019312326 KimJunyoung"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              "CORONA LIVE",
              style: TextStyle(color: Colors.blueAccent, fontSize: 30),
            ),
            Text(
              "Login Please",
              style: TextStyle(fontSize: 20),
            ),
            SizedBox(
              height: 50,
            ),
            Container(
              width: 300,
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(20),
                  border: Border.all(color: Colors.blue, width: 2)),
              child: Padding(
                padding: EdgeInsets.all(20),
                child: Column(
                  children: [
                    TextField(
                      controller: _idController,
                      decoration: InputDecoration(labelText: 'ID'),
                    ),
                    TextField(
                      controller: _pwController,
                      obscureText: true,
                      decoration: InputDecoration(labelText: 'PW'),
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    ElevatedButton(onPressed: _login, child: Text('Login'))
                  ],
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}
