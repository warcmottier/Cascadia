package game;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public final class DrawSquare {
  private final Map<TileSquare, WildlifeToken> draw = new HashMap<>();
  private final Map<WildlifeToken, Integer> wildlife = new HashMap<>();
  private final List<TileSquare> tiles = new ArrayList<>();
  
  public Map<TileSquare, WildlifeToken>  draw() {
    return draw;
  }
  
  public Map<WildlifeToken, Integer> wildlife() {
    return wildlife;
  }
  
  public List<TileSquare> tiles() {
    return tiles;
  }
  
  private void initializeWildlifeToken() {
    wildlife.put(WildlifeToken.BEAR, 20);
    wildlife.put(WildlifeToken.SALMON, 20);
    wildlife.put(WildlifeToken.ELK, 20);
    wildlife.put(WildlifeToken.FOX, 20);
    wildlife.put(WildlifeToken.NOZZLE, 20);
  }
  
  private Set<WildlifeToken> generateAnimalAccepted() {
    Set<WildlifeToken> animalaccepted = new HashSet<>();
    var wildlife = WildlifeToken.values();
    var random = new Random();
    for(animalaccepted.add(wildlife[random.nextInt(wildlife.length)]); animalaccepted.size() != 2;) {
      animalaccepted.add(wildlife[random.nextInt(wildlife.length)]);
    }
    return animalaccepted;
  }
  
  private void initializeTiles() {
    var landscape = Landscape.values();
    var random = new Random();
    for(var i = 0; i < 43; i++) {
      tiles.add(new TileSquare(generateAnimalAccepted(), null, landscape[random.nextInt(landscape.length)]));
    }
  }
  
  private void initializeDraw() {
    var random = new Random();
    TileSquare tile;
    WildlifeToken animal;
    for(var i = 0; i < 4; i++) {      
      tile = tiles.get(random.nextInt(tiles.size()));
      animal = drawWildlifeToken();
      draw.put(tile, animal);
      tiles.remove(tile);
    }
  }
  
  public Map<TileSquare, WildlifeToken> pickDraw(int picked) {
    Objects.checkIndex(picked - 1, draw.size());
    var chosenKeys = new ArrayList<>(draw.keySet());
    TileSquare selectedKey = chosenKeys.get(picked - 1);
    WildlifeToken selectedAnimal = draw.get(selectedKey);
    Map<TileSquare, WildlifeToken> chosendraw = new HashMap<>();
    chosendraw.put(selectedKey, selectedAnimal);
    draw.remove(selectedKey);
    refillDraw();
    return chosendraw;
  }

  private Map<WildlifeToken, Long> initializeValueCount() {
    var valuecount = draw.values().stream()
        .collect(Collectors.groupingBy(animal -> animal, Collectors.counting()));
    return valuecount;
  }
  
  public int isOverpopulation() {
    var valuecount = initializeValueCount();
    return valuecount.values().stream()
        .filter(count -> count == 3 || count == 4)
        .mapToInt(Long::intValue)
        .findFirst()
        .orElse(0);
  }
  
  private WildlifeToken removeOverpopulation(int number) {
    var tiles = draw.keySet().toArray();
    var wildlife = draw.get(tiles[0]);
    if(number == 4) {
      return wildlife;
    }
    var count = draw.values().stream().filter(elements -> wildlife == elements).count();
    if(count == 3) {
      return wildlife;
    }
    return draw.get(tiles[1]);
  }
  
  private WildlifeToken drawWildlifeToken() {
    var random = new Random();
    var animal = WildlifeToken.values()[random.nextInt(WildlifeToken.values().length)];
    for(; wildlife.get(animal) == 0; animal = WildlifeToken.values()[random.nextInt(WildlifeToken.values().length)]);
    wildlife.put(animal, wildlife.get(animal) - 1);
    return animal;
  }
  
  public void overpopulation(int number) {
    var animal = removeOverpopulation(number);
    if(number == 4) {
      draw.entrySet().stream()
      .filter(entry -> entry.getValue().equals(animal))
      .forEach(entry -> {
          WildlifeToken newWildlife = drawWildlifeToken();
          entry.setValue(newWildlife);
      });
      wildlife.put(animal, wildlife.get(animal) + 4);
    }
    else {
      draw.entrySet().stream()
      .filter(entry -> entry.getValue().equals(animal))
      .forEach(entry -> {
          WildlifeToken newWildlife = drawWildlifeToken();
          entry.setValue(newWildlife);
      });
      wildlife.put(animal, wildlife.get(animal) + 3);
    }
  }
  
  private void refillDraw() {
    var random = new Random();
    var tile = tiles.get(random.nextInt(tiles.size()));
    tiles.remove(tile);
    var animal = drawWildlifeToken();
    draw.put(tile, animal);
  }
  
  public static DrawSquare createDraw() {
    var draw = new DrawSquare();
    draw.initializeWildlifeToken();
    draw.initializeTiles();
    draw.initializeDraw();
    return draw;
  }
  
  @Override
  public String toString() {
      List<String> formattedLines = new ArrayList<>();
      var index = new AtomicInteger(1); // Usage of AtomicInteger to keep the index inside the stream
      draw.entrySet().forEach(entry -> {
          TileSquare tile = entry.getKey();
          Set<WildlifeToken> animalsAccepted = tile.animalAccepted();
          String animalAcceptedStr = animalsAccepted.stream()
              .map(WildlifeToken::name)
              .collect(Collectors.joining(", "));
          WildlifeToken wildlifeToken = entry.getValue();
          String wildlifeTokenStr = wildlifeToken != null ? wildlifeToken.name() : "None";
          String line = String.format("%s, animal accepted: %s + Wildlife token: %s", tile.landscape(), animalAcceptedStr, wildlifeTokenStr);
          formattedLines.add(String.format("-%d %s", index.getAndIncrement(), line));
      });// We join everything with \n
      return String.join("\n", formattedLines);
  }

}
