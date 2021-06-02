import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:pa3/navigation.dart';

class LoginProvider with ChangeNotifier {
  int login = 0;
  String id = "";

  void logincheck(String _id, String _pw) {
    this.id = _id;
    if (_id == "skku" && _pw == "1234") {
      login = 1;
      notifyListeners();
    }
  }
}

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => LoginProvider(),
      child: MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        initialRoute: '/',
        onGenerateRoute: (routerSettings) {
          switch (routerSettings.name) {
            case '/':
              return MaterialPageRoute(builder: (_) => LoginPage());
              break;
            case '/navigation':
              return MaterialPageRoute(
                  builder: (_) => Navigation(routerSettings.arguments));
              break;
            default:
              return MaterialPageRoute(
                  builder: (_) => Navigation(routerSettings.arguments));
              break;
          }
        },
      ),
    );
  }
}

class LoginPage extends StatelessWidget {
  final _idController = TextEditingController();
  final _pwController = TextEditingController();

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
              style: TextStyle(color: Colors.blueGrey, fontSize: 30),
            ),
            Consumer<LoginProvider>(builder: (context, _loginprovider, child) {
              if (_loginprovider.login == 0) {
                return Column(
                  children: [
                    Text(
                      "Login Please",
                      style: TextStyle(fontSize: 20, color: Colors.grey),
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
                            ElevatedButton(
                                onPressed: () => _loginprovider.logincheck(
                                    _idController.text, _pwController.text),
                                child: Text('Login'))
                          ],
                        ),
                      ),
                    ),
                  ],
                );
              } else {
                return Column(
                  children: [
                    Text(
                      "Login Success. Hello ${_loginprovider.id}!",
                      style: TextStyle(fontSize: 20, color: Colors.blueAccent),
                    ),
                    SizedBox(
                      height: 50,
                    ),
                    Image.asset("assets/images/main.jpg"),
                    SizedBox(
                      height: 10,
                    ),
                    ElevatedButton(
                        onPressed: () {
                          Navigator.pushNamed(context, "/navigation",
                              arguments: {"id": _loginprovider.id.toString()});
                        },
                        child: Text("Start CORONA LIVE"))
                  ],
                );
              }
            }),
          ],
        ),
      ),
    );
  }
}
