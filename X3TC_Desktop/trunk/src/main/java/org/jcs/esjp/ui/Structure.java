package org.jcs.esjp.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeMap;

import org.jcs.esjp.model.ObjectPurchase;
import org.jcs.esjp.model.ObjectSale;
import org.jcs.esjp.model.Race;
import org.jcs.esjp.model.Sector;
import org.jcs.esjp.model.StructureAbstract;
import org.jcs.esjp.model.StructureFactory;
import org.jcs.esjp.model.StructureFreeShip;
import org.jcs.esjp.model.StructureGate;
import org.jcs.esjp.model.StructureNormal;
import org.jcs.esjp.model.StructureOther;
import org.jcs.esjp.util.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Structure {
    private static final Logger LOG = LoggerFactory.getLogger(Structure.class);

    private Integer maxX;
    private Integer maxY;

    public Map<Integer, Map<Integer, Sector>> init()
        throws IOException
    {
        maxX = 0;
        maxY = 0;

        final Properties prop = new Properties();
        try {
            prop.load(Structure.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (final IOException e1) {
            e1.printStackTrace();
        }

        final String filePath = prop.getProperty("org.jcs.esjp.file");
        final Path path = Paths.get(filePath);
        final Charset encoding = Charset.forName(prop.getProperty("org.jcs.esjp.charset"));
        final Scanner scanner = new Scanner(path, encoding.name());
        final List<Sector> lstSectors = buildAll(scanner);
        scanner.close();

        final Map<Integer, Map<Integer, Sector>> matrix = buildMatrix(lstSectors);

        return matrix;
    }

    protected List<Sector> buildAll(final Scanner scanner)
    {
        final List<Sector> lstSect = new ArrayList<Sector>();
        String line = scanner.nextLine();
        while (scanner.hasNextLine()) {
            line = analize(line, scanner, null, lstSect);
        }
        return lstSect;
    }

    protected String analize(final String _line,
                             final Scanner _scanner,
                             final Object _object,
                             final List<Sector> _lstSector) {
        String line = _line;
        if ("==========".equals(_line) && _object == null) {
            final Sector sector = new Sector();
            line = buildSector(sector, _scanner);
            _lstSector.add(sector);
            line = analize(line, _scanner, sector, _lstSector);
        } else if (Settings.FactorySettings.ASTILLERO.getKey().equals(_line)
                        || Settings.FactorySettings.ASTILLERO.getKey().equals(_line)
                        || Settings.FactorySettings.ESTACION_COMERCIAL.getKey().equals(_line)
                        || Settings.FactorySettings.MUELLE.getKey().equals(_line)) {
            line = buildNormal((Sector) _object, _scanner, _line);
            line = analize(line, _scanner, _object, _lstSector);
        } else if (Settings.FactorySettings.FACTORIA.getKey().equals(_line)) {
            line = buildFactory((Sector) _object, _scanner);
            line = analize(line, _scanner, _object, _lstSector);
        } else if (Settings.FactorySettings.HIELO.getKey().equals(line)
                        || Settings.FactorySettings.MENA.getKey().equals(line)
                        || Settings.FactorySettings.NVIDIUM.getKey().equals(line)
                        || Settings.FactorySettings.SILICIO.getKey().equals(line)) {
            line = buildOther((Sector) _object, _scanner);
            line = analize(line, _scanner, _object, _lstSector);
        } else if (Settings.FactorySettings.VACIO.getKey().equals(line)) {
            line = buildFreeShip((Sector) _object, _scanner);
            line = analize(line, _scanner, _object, _lstSector);
        } else if (line.startsWith("Log-file")) {
            line = _scanner.nextLine();
        }
        return line;
    }

    protected String buildSector(final Sector _sector,
                                 final Scanner _scanner) {
        String line = _scanner.nextLine();
        _sector.setName(line);
        Structure.LOG.info("Nombre del Sector: '{}'", _sector.getName());
        final Integer posY = Integer.parseInt(_scanner.nextLine());
        _sector.setPosY(posY);
        final Integer posX = Integer.parseInt(_scanner.nextLine());
        _sector.setPosX(posX);

        final Race race = new Race();
        race.setName(_scanner.nextLine());
        race.setColor(Settings.RACE2COLOR.get(race.getName()));

        System.out.println("Nombre del Sector: "  + _sector.getName() + " X=" + _sector.getPosX() + " Y=" + _sector.getPosY());
        _sector.setRace(race);

        // I don't know what is this yet.
        _scanner.nextLine();
        _scanner.nextLine();
        line = _scanner.nextLine();
        while (!"==========".equals(line)
                        && !Settings.FactorySettings.ASTILLERO.getKey().equals(line)
                        && !Settings.FactorySettings.FACTORIA.getKey().equals(line)
                        && !Settings.FactorySettings.ESTACION_COMERCIAL.getKey().equals(line)
                        && !Settings.FactorySettings.MUELLE.getKey().equals(line)
                        && !Settings.FactorySettings.HIELO.getKey().equals(line)
                        && !Settings.FactorySettings.MENA.getKey().equals(line)
                        && !Settings.FactorySettings.NVIDIUM.getKey().equals(line)
                        && !Settings.FactorySettings.SILICIO.getKey().equals(line)
                        && !Settings.FactorySettings.VACIO.getKey().equals(line)) {
            final StructureGate gate = new StructureGate();
            final String[] objs = line.split("; ");
            gate.setPosX(Integer.parseInt(objs[0]));
            gate.setPosY(Integer.parseInt(objs[1]));
            gate.setPosZ(Integer.parseInt(objs[2]));
            gate.setFromID(objs[6]);
            gate.setToID(objs[7]);
            _sector.getLstGates().add(gate);

            line = _scanner.nextLine();
        }

        return line;
    }

    protected String buildNormal(final Sector _sector,
                                 final Scanner _scanner,
                                 final String _curLine) {
        String line = _scanner.nextLine();
        while (!"==========".equals(line)
                        && !Settings.FactorySettings.ASTILLERO.getKey().equals(line)
                        && !Settings.FactorySettings.FACTORIA.getKey().equals(line)
                        && !Settings.FactorySettings.ESTACION_COMERCIAL.getKey().equals(line)
                        && !Settings.FactorySettings.MUELLE.getKey().equals(line)
                        && !Settings.FactorySettings.HIELO.getKey().equals(line)
                        && !Settings.FactorySettings.MENA.getKey().equals(line)
                        && !Settings.FactorySettings.NVIDIUM.getKey().equals(line)
                        && !Settings.FactorySettings.SILICIO.getKey().equals(line)
                        && !Settings.FactorySettings.VACIO.getKey().equals(line)) {
            final StructureNormal normalStruc = new StructureNormal();
            final String[] objs = line.split("; ");
            normalStruc.setName(objs[0]);
            if (Settings.FactorySettings.ASTILLERO.getKey().equals(_curLine)) {
                normalStruc.setIconPath("src/main/resources/images/data/shipyard.png");
            } else if (Settings.FactorySettings.ESTACION_COMERCIAL.getKey().equals(_curLine)) {
                normalStruc.setIconPath("src/main/resources/images/data/tradeStation.png");
            } else if (Settings.FactorySettings.MUELLE.getKey().equals(_curLine)) {
                normalStruc.setIconPath("src/main/resources/images/data/equipmentDock.png");
            }
            normalStruc.setPosX(Integer.parseInt(objs[1]));
            normalStruc.setPosY(Integer.parseInt(objs[2]));
            normalStruc.setPosZ(Integer.parseInt(objs[3]));

            final Race race = new Race();
            race.setName(_scanner.nextLine());
            race.setColor(Settings.RACE2COLOR.get(race.getName()));
            normalStruc.setRace(race);

            line = buildSalePurchase(_scanner, normalStruc);

            _sector.getLstStruct().add(normalStruc);
        }
        return line;
    }

    protected String buildFactory(final Sector _sector,
                                  final Scanner _scanner) {
        String line = _scanner.nextLine();
        while (!"==========".equals(line)
                        && !Settings.FactorySettings.ASTILLERO.getKey().equals(line)
                        && !Settings.FactorySettings.FACTORIA.getKey().equals(line)
                        && !Settings.FactorySettings.ESTACION_COMERCIAL.getKey().equals(line)
                        && !Settings.FactorySettings.MUELLE.getKey().equals(line)
                        && !Settings.FactorySettings.HIELO.getKey().equals(line)
                        && !Settings.FactorySettings.MENA.getKey().equals(line)
                        && !Settings.FactorySettings.NVIDIUM.getKey().equals(line)
                        && !Settings.FactorySettings.SILICIO.getKey().equals(line)
                        && !Settings.FactorySettings.VACIO.getKey().equals(line)) {
            final StructureFactory factory = new StructureFactory();
            final String[] objs = line.split("; ");
            factory.setName(objs[0]);
            factory.setIconPath("src/main/resources/images/data/factory.png");
            factory.setPosX(Integer.parseInt(objs[1]));
            factory.setPosY(Integer.parseInt(objs[2]));
            factory.setPosZ(Integer.parseInt(objs[3]));

            final Race race = new Race();
            race.setName(_scanner.nextLine());
            race.setColor(Settings.RACE2COLOR.get(race.getName()));
            factory.setRace(race);

            line = buildSalePurchase(_scanner, factory);

            _sector.getLstStruct().add(factory);
        }
        return line;
    }

    protected String buildOther(final Sector _sector,
                                final Scanner _scanner) {
        String line = _scanner.nextLine();
        while (!"==========".equals(line)
                        && !Settings.FactorySettings.ASTILLERO.getKey().equals(line)
                        && !Settings.FactorySettings.FACTORIA.getKey().equals(line)
                        && !Settings.FactorySettings.ESTACION_COMERCIAL.getKey().equals(line)
                        && !Settings.FactorySettings.MUELLE.getKey().equals(line)
                        && !Settings.FactorySettings.HIELO.getKey().equals(line)
                        && !Settings.FactorySettings.MENA.getKey().equals(line)
                        && !Settings.FactorySettings.NVIDIUM.getKey().equals(line)
                        && !Settings.FactorySettings.SILICIO.getKey().equals(line)
                        && !Settings.FactorySettings.VACIO.getKey().equals(line)) {
            final StructureOther other = new StructureOther();
            final String[] objs = line.split("; ");
            other.setName(objs[0].replace("+ ", "").replace("+", ""));
            //System.out.println("Nombre del Other: "  + other.getName());
            other.setPosX(Integer.parseInt(objs[1]));
            other.setPosY(Integer.parseInt(objs[2]));
            other.setPosZ(Integer.parseInt(objs[3]));

            line = _scanner.nextLine();
            _sector.getLstStruct().add(other);
        }
        return line;
    }

    protected String buildFreeShip(final Sector _sector,
                                   final Scanner _scanner) {
        String line = _scanner.nextLine();
        while (!"==========".equals(line)
                        && !Settings.FactorySettings.ASTILLERO.getKey().equals(line)
                        && !Settings.FactorySettings.FACTORIA.getKey().equals(line)
                        && !Settings.FactorySettings.ESTACION_COMERCIAL.getKey().equals(line)
                        && !Settings.FactorySettings.MUELLE.getKey().equals(line)
                        && !Settings.FactorySettings.HIELO.getKey().equals(line)
                        && !Settings.FactorySettings.MENA.getKey().equals(line)
                        && !Settings.FactorySettings.NVIDIUM.getKey().equals(line)
                        && !Settings.FactorySettings.SILICIO.getKey().equals(line)
                        && !Settings.FactorySettings.VACIO.getKey().equals(line)) {
            final StructureFreeShip freeship = new StructureFreeShip();
            final String[] objs = line.split("; ");
            freeship.setName(objs[0].replace("+ ", "").replace("+", "") + " " + objs[1]);
            //System.out.println("Nombre de la free ship: "  + freeship.getName());
            freeship.setPosX(Integer.parseInt(objs[2]));
            freeship.setPosY(Integer.parseInt(objs[3]));
            freeship.setPosZ(Integer.parseInt(objs[4]));

            line = _scanner.nextLine();
            _sector.getLstStruct().add(freeship);
        }
        return line;
    }

    protected String buildSalePurchase(final Scanner _scanner,
                                       final StructureAbstract _structure) {
        String lineObjs = _scanner.nextLine();
        while (lineObjs.startsWith("+")) {
            final ObjectSale strucSale = new ObjectSale();
            final String[] lineObjsArr = lineObjs.split("; ");
            strucSale.setName(lineObjsArr[0].replace("+ ", "").replace("+", ""));
            strucSale.setIconPath("src/main/resources/images/data/sale.png");
            strucSale.setPrice(new BigDecimal(lineObjsArr[1]));
            strucSale.setQuantity(Integer.parseInt(lineObjsArr[2]));
            strucSale.setFreeSpace(Integer.parseInt(lineObjsArr[3]));

            if (_structure instanceof StructureNormal) {
                ((StructureNormal) _structure).getObjSale().add(strucSale);
            } else if (_structure instanceof StructureFactory) {
                ((StructureFactory) _structure).getObjSale().add(strucSale);
            }

            lineObjs = _scanner.nextLine();
        }
        while (lineObjs.startsWith("*")) {
            final ObjectPurchase strucPur = new ObjectPurchase();
            final String[] lineObjsArr = lineObjs.split("; ");
            strucPur.setName(lineObjsArr[0].replace("* ", "").replace("*", ""));
            strucPur.setIconPath("src/main/resources/images/data/purchase.png");
            strucPur.setPrice(new BigDecimal(lineObjsArr[1]));
            strucPur.setQuantity(Integer.parseInt(lineObjsArr[2]));
            strucPur.setFreeSpace(Integer.parseInt(lineObjsArr[3]));

            if (_structure instanceof StructureFactory) {
                ((StructureFactory) _structure).getObjPurch().add(strucPur);
            }

            lineObjs = _scanner.nextLine();
        }
        return lineObjs;
    }

    protected Map<Integer, Map<Integer, Sector>> buildMatrix(final List<Sector> _sectors)
    {
        final Map<Integer, Map<Integer, Sector>> rows = new TreeMap<Integer, Map<Integer, Sector>>();
        for(final Sector sector: _sectors) {
            final Integer posX = sector.getPosX();
            /*if (!Settings.RaceSettings.TERRAN.getKey().equals(sector.getRace().getName())) {
                posX = sector.getPosX() + 6;
            }*/
            final Integer posY = sector.getPosY();
            if (posX > maxX) {
                maxX = posX;
            }
            if (posY > maxY) {
                maxY = posY;
            }
            if (rows.containsKey(posX)) {
                final Map<Integer, Sector> cols = rows.get(posX);
                if (cols.containsKey(posY)) {
                    Structure.LOG.warn("Aqui pasa algo raro O.o");
                } else {
                    cols.put(posY, sector);
                }
            } else {
                final Map<Integer, Sector> cols = new TreeMap<Integer, Sector>();
                cols.put(posY, sector);
                rows.put(posX, cols);
            }
        }
        return rows;
    }

    public Integer getMaxX() {
        return maxX;
    }

    public Integer getMaxY() {
        return maxY;
    }



}
