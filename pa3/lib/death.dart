import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class GraphProvider with ChangeNotifier {
  int graph = 1;
  GraphProvider(this.graph);
  void setGraph(n) {
    graph = n;
    notifyListeners();
  }
}

class TableProvider with ChangeNotifier {
  int table = 1;
  TableProvider(this.table);
  void setTable(n) {
    table = n;
    notifyListeners();
  }
}

class Death extends StatelessWidget {
  final Map<String, String> arguments;
  Death(this.arguments);

  @override
  Widget build(BuildContext context) {
    return MultiProvider(providers: [
      ChangeNotifierProvider(create: (context) => GraphProvider(1)),
      ChangeNotifierProvider(create: (context) => TableProvider(1))
    ], child: DeathPage(id: arguments['id']));
  }
}

class DeathPage extends StatelessWidget {
  final String id;
  DeathPage({Key key, this.id}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    GraphProvider gp = Provider.of<GraphProvider>(context);
    TableProvider tp = Provider.of<TableProvider>(context);

    return Scaffold(
      body: Center(
        child: Column(
          children: [
            SizedBox(
              height: 50,
            ),

            //첫번째
            Container(
              width: 350,
              height: 120,
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(20),
                  border: Border.all(color: Colors.blueGrey, width: 2)),
              child: Padding(
                padding: EdgeInsets.all(5),
                child: Column(
                  children: [
                    Expanded(
                      child: Row(
                        children: [
                          Expanded(child: Text("dsf")),
                          Expanded(child: Text("dsf"))
                        ],
                      ),
                    ),
                    Expanded(
                      child: Row(
                        children: [
                          Expanded(child: Text("dsf")),
                          Expanded(child: Text("dsf"))
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),
            SizedBox(
              height: 20,
            ),

            //두번째(graph)
            Container(
              width: 350,
              height: 250,
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(20),
                  border: Border.all(color: Colors.blueGrey, width: 2)),
              child: Padding(
                padding: EdgeInsets.all(5),
                child: Column(
                  children: [
                    Expanded(
                      child: Row(
                        children: [
                          Expanded(
                            child: TextButton(
                                onPressed: () => gp.setGraph(1),
                                child: Text("Graph 1")),
                          ),
                          Expanded(
                            child: TextButton(
                                onPressed: () => gp.setGraph(2),
                                child: Text("Graph 2")),
                          ),
                          Expanded(
                            child: TextButton(
                                onPressed: () => gp.setGraph(3),
                                child: Text("Graph 3")),
                          ),
                          Expanded(
                            child: TextButton(
                                onPressed: () => gp.setGraph(4),
                                child: Text("Graph 4")),
                          ),
                        ],
                      ),
                      flex: 1,
                    ),
                    Divider(
                      color: Colors.blueGrey,
                      thickness: 2,
                    ),
                    Consumer<GraphProvider>(
                      builder: (context, gp, child) => Expanded(
                        child: Text("${gp.graph}"),
                        flex: 5,
                      ),
                    ),
                  ],
                ),
              ),
            ),
            SizedBox(
              height: 20,
            ),

            //세번째 (table)
            Container(
              width: 350,
              height: 250,
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(20),
                  border: Border.all(color: Colors.blueGrey, width: 2)),
              child: Padding(
                padding: EdgeInsets.all(20),
                child: Column(
                  children: [
                    Expanded(
                      child: Row(
                        children: [
                          Expanded(
                            child: TextButton(
                                onPressed: () => tp.setTable(1),
                                child: Text("Table 1")),
                          ),
                          Expanded(
                            child: TextButton(
                                onPressed: () => tp.setTable(2),
                                child: Text("Table 2")),
                          ),
                        ],
                      ),
                      flex: 1,
                    ),
                    Divider(
                      color: Colors.blueGrey,
                      thickness: 2,
                    ),
                    Consumer<TableProvider>(
                      builder: (context, tp, child) => Expanded(
                        child: Text("${tp.table}"),
                        flex: 5,
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.pushNamed(context, "/navigation",
              arguments: {"id": id, "previous": "Case/Death Page"});
        },
        child: Icon(Icons.list),
      ),
    );
  }
}
