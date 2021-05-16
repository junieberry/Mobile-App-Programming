import 'package:flutter/material.dart';
import 'package:week11/page/page1.dart';
import 'package:week11/page/page2.dart';
import 'package:week11/page/page3.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),

      // routes: {
      //   '/':(context)=>MyHomePage(),
      //   '/page1':(context)=>Page1(),
      //   '/page2':(context)=>Page2(),
      //   '/page3':(context)=>Page3(),
      // },


      onGenerateRoute: (routerSettings){
        switch(routerSettings.name){
          case '/':
            return MaterialPageRoute(builder: (_) => MyHomePage(title:"week 11"));
          case 'page1':
            return MaterialPageRoute(builder: (_) => Page1(routerSettings.arguments));
            break;
          case 'page2':
            return MaterialPageRoute(builder: (_) => Page2());
            break;
          case 'page3':
            return MaterialPageRoute(builder: (_) => Page3());
            break;
          default:
            return MaterialPageRoute(builder: (_) => MyHomePage(title:"Error unknown Route"));
        }
      },
      initialRoute: '/',
    );


  }
}
class MyHomePage extends StatelessWidget {
  final String title;

  const MyHomePage({Key key, this.title}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title:Text('week11'),
        ),
        body:Center(
            child:Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                ElevatedButton(
                  child:Text('Move to Page 1'),
                  onPressed: (){
                    Navigator.pushNamed(
                        context,
                        '/page1',
                    arguments:{"user-msg1":"hihi", "user-msg2":"byebye"});
                  },
                ),
                ElevatedButton(
                  child:Text('Move to Page 2'),
                  onPressed: (){
                    Navigator.pushNamed(context, '/page2');
                  },
                ),
                ElevatedButton(
                  child:Text('Move to Page 3'),
                  onPressed: (){
                    Navigator.pushNamed(context, '/page3');
                  },
                ),
                ElevatedButton(
                  child:Text('Unknown'),
                  onPressed: (){
                    Navigator.pushNamed(context, 'unknown');
                  },
                ),
              ],
            )
        )
    );
  }
}

// class MyHomePage extends StatefulWidget {
//   final String title;
//
//   const MyHomePage({Key key, this.title}) : super(key: key);
//   @override
//   _MyHomePageState createState() => _MyHomePageState();
// }
//
// class _MyHomePageState extends State<MyHomePage> {
//   @override
//   Widget build(BuildContext context) {
//     return
//   }
// }
//
