import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

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
        // This is the theme of your application.

        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<String> _data=[];

  TextEditingController txtController=TextEditingController();

  void _addlist(){
    setState(() {
      _data.add(txtController.text);
    });
  }

  Widget _buildListView(){
    return ListView.builder(
        scrollDirection: Axis.vertical,
        shrinkWrap: true, //사이즈 조정하기 위함 (match_parent)

        itemCount: _data.length,//필수
        itemBuilder: (BuildContext _ctx, int i){
          return ListTile(
            // leading, title, subtitle, trailing
            title: Text(_data[i],
                style: TextStyle(fontSize: 23, color: Colors.indigo)),
          );
        }//필수
    );
  }


  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(

        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
          child:
          SingleChildScrollView(
            scrollDirection: Axis.vertical,
            child:Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    SizedBox(
                      width:200,
                      child : TextField(
                        controller: txtController,
                      ),),
                    ElevatedButton(
                        onPressed:()=> _addlist(),
                        child: Text("Put"))

                  ],
                ),
                Container(
                  height: 500,
                  child:_buildListView(),
                )

              ],
            ),
          )

      ),
      // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
