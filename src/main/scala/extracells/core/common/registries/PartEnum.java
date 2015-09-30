package extracells.core.common.registries;

import appeng.api.config.Upgrades;
import extracells.core.common.part.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public enum PartEnum {
	/*FLUIDEXPORT("fluid.export", PartFluidExport.class, "fluid.IO", generatePair(Upgrades.CAPACITY, 2), generatePair(Upgrades.REDSTONE, 1), generatePair(Upgrades.SPEED, 2)),
	FLUIDIMPORT("fluid.import", PartFluidImport.class, "fluid.IO", generatePair(Upgrades.CAPACITY, 2), generatePair(Upgrades.REDSTONE, 1), generatePair(Upgrades.SPEED, 2)),
	FLUIDSTORAGE("fluid.storage", PartFluidStorage.class, null, generatePair(Upgrades.INVERTER, 1)),
	FLUIDTERMINAL("fluid.terminal", PartFluidTerminal.class),
	FLUIDLEVELEMITTER("fluid.levelemitter", PartFluidLevelEmitter.class),
	FLUIDPANEANNIHILATION("fluid.plane.annihilation", PartFluidPlaneAnnihilation.class, "fluid.plane"),
	FLUIDPANEFORMATION("fluid.plane.formation", PartFluidPlaneFormation.class, "fluid.plane"),
	DRIVE("drive", PartDrive.class),
	BATTERY("battery", PartBattery.class),
	INTERFACE("interface", PartFluidInterface.class),
	FLUIDMONITOR("fluid.monitor", PartStorageMonitor.class),
	FLUIDCONVERSIONMONITOR("fluid.conversion.monitor", PartConversionMonitor.class),
	OREDICTEXPORTBUS("oredict.export", PartOreDictExporter.class),
	GASIMPORT("gas.import", PartGasImport.class, "gas.IO", generatePair(Upgrades.CAPACITY, 2), generatePair(Upgrades.REDSTONE, 1), generatePair(Upgrades.SPEED, 2)),
	GASEXPORT("gas.export", PartGasExport.class, "gas.IO", generatePair(Upgrades.CAPACITY, 2), generatePair(Upgrades.REDSTONE, 1), generatePair(Upgrades.SPEED, 2)),
	GASTERMINAL("gas.terminal", PartGasTerminal.class);*/;


	private static Pair<Upgrades, Integer> generatePair(Upgrades _upgrade,
			int integer) {
		return new MutablePair<Upgrades, Integer>(_upgrade, integer);
	}

	public static int getPartID(Class<? extends PartECBase> partClass) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].getPartClass() == partClass)
				return i;
		}
		return -1;
	}

	public static int getPartID(PartECBase partECBase) {
		return getPartID(partECBase.getClass());
	}

	private String unlocalizedName;

	private Class<? extends PartECBase> partClass;

	private String groupName;

	private Map<Upgrades, Integer> upgrades = new HashMap<Upgrades, Integer>();

	PartEnum(String _unlocalizedName, Class<? extends PartECBase> _partClass) {
		this(_unlocalizedName, _partClass, null);
	}

	PartEnum(String _unlocalizedName, Class<? extends PartECBase> _partClass,
			 String _groupName) {
		this.unlocalizedName = "extracells.part." + _unlocalizedName;
		this.partClass = _partClass;
		this.groupName = _groupName == null || _groupName.isEmpty() ? null
				: "extracells." + _groupName;
	}

	PartEnum(String _unlocalizedName, Class<? extends PartECBase> _partClass,
			 String _groupName, Pair<Upgrades, Integer>... _upgrades) {
		this(_unlocalizedName, _partClass, _groupName);
		for (Pair<Upgrades, Integer> pair : _upgrades) {
			this.upgrades.put(pair.getKey(), pair.getValue());
		}
	}

	public String getGroupName() {
		return this.groupName;
	}

	public Class<? extends PartECBase> getPartClass() {
		return this.partClass;
	}

	public String getStatName() {
		return StatCollector.translateToLocal(this.unlocalizedName + ".name");
	}

	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}

	@SuppressWarnings("unchecked")
	public Map<Upgrades, Integer> getUpgrades() {
		return this.upgrades;
	}

	public PartECBase newInstance(ItemStack partStack)
			throws IllegalAccessException, InstantiationException {
		PartECBase partECBase = this.partClass.newInstance();
		partECBase.initializePart(partStack);
		return partECBase;
	}
}
