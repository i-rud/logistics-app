package com.ruddi.logiweb.bl;

import com.ruddi.logiweb.dto.CargoDto;
import com.ruddi.logiweb.dto.CountryDto;
import com.ruddi.logiweb.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphServiceImpl implements GraphService {
    @Override
    public List<String> getPath(OrderDto order, List<CountryDto> graph) {

        String destinationCity = null;
        List<String> toVisit = new ArrayList<>();

        for (CargoDto cargo : order.getCargos()) {
            if (toVisit.isEmpty() && cargo.getDestination().equals(toVisit.get(0))) {
                destinationCity = cargo.getDestination();
            }

            if (!toVisit.contains(cargo.getDeparture())) {
                toVisit.add(cargo.getDeparture());
            }
            if (!toVisit.contains(cargo.getDestination())) {
                toVisit.add(cargo.getDestination());
            }
        }

        List<String> apexes = new ArrayList<>();
        for (CountryDto pair : graph) {
            if (!apexes.contains(pair.getDeparture()))
                apexes.add(pair.getDeparture());
            if (!apexes.contains(pair.getDestination()))
                apexes.add(pair.getDestination());
        }

        ArrayList<Integer>[] adjList = new ArrayList[apexes.size()];

        for (int i = 0; i < apexes.size(); ++i) {
            adjList[i] = new ArrayList<>();
            for (CountryDto pair : graph) {
                if (apexes.get(i).equals(pair.getDeparture()))
                    adjList[i].add(apexes.indexOf(pair.getDestination()));
                if (apexes.get(i).equals(pair.getDestination()))
                    adjList[i].add(apexes.indexOf(pair.getDeparture()));
            }
        }

        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        boolean[] visited = new boolean[apexes.size()];

        dfs(visited, adjList, result, path, apexes.indexOf(toVisit.get(0)),
                apexes.indexOf(toVisit.get(toVisit.size() - 1)));

        List<List<String>> stringResult = new ArrayList<>();
        for (ArrayList<Integer> a : result) {
            if (a.size() < toVisit.size())
                continue;

            List<String> buf = new ArrayList<>();
            for (Integer integer : a) buf.add(apexes.get(integer));
            stringResult.add(buf);
        }

        int minCost = Integer.MAX_VALUE;
        List<String> bestPath = new ArrayList<>();

        for (List<String> onePath : stringResult) {
            boolean successFlag = true;
            for (String s : toVisit) {
                if (!onePath.contains(s)) {
                    successFlag = false;
                    break;
                }
            }
            if (successFlag) {
                int cost = countCost(onePath, graph);
                if(cost <= minCost) {
                    minCost = cost;
                    bestPath = new ArrayList<>(onePath);
                }
            }
        }

        if (destinationCity != null)
            bestPath.add(destinationCity);

        return bestPath;
    }

    @Override
    public int countCost(List<String> path, List<CountryDto> graph) {
        int result = 0;

        for (int i = 0; i < path.size() - 1; ++i) {
            String departure = path.get(i);
            String destination = path.get(i + 1);

            for (CountryDto pair : graph) {
                if ((departure.equals(pair.getDeparture())
                        && destination.equals(pair.getDestination()))
                        || (departure.equals(pair.getDestination())
                        && destination.equals(pair.getDeparture()))) {
                    result += pair.getDistance();
                }
            }
        }

        return result;
    }

    void dfs(boolean[] visited, ArrayList<Integer>[] adj, ArrayList<ArrayList<Integer>> result,
             ArrayList<Integer> path, int vertex, int finish) {
        path.add(vertex);
        visited[vertex] = true;
        if (vertex == finish) {
            result.add(new ArrayList<>(path));
        } else {
            for (Integer a : adj[vertex]) {
                if (!visited[a]) {
                    dfs(visited, adj, result, path, a, finish);
                    visited[path.get(path.size() - 1)] = false;
                    path.remove(path.size() - 1);
                }
            }
        }
    }
}