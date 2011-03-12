package com.elmakers.mine.bukkit.gameplay;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.elmakers.mine.bukkit.gameplay.dao.BlockList;
import com.elmakers.mine.bukkit.gameplay.dao.MaterialData;
import com.elmakers.mine.bukkit.gameplay.dao.MaterialList;

public class ReplaceMaterialAction implements BlockRecurseAction
{
	public ReplaceMaterialAction(Material replaceMaterial, byte replaceData)
	{
		replace = new MaterialData(replaceMaterial, replaceData);
	}

	public ReplaceMaterialAction(Block targetBlock, Material replaceMaterial, byte replaceData)
	{
		
		replaceable.add(new MaterialData(targetBlock.getType(), targetBlock.getData()));
		replace = new MaterialData(replaceMaterial, replaceData);
	}
	
	public ReplaceMaterialAction(Material targetMaterial, byte targetData, Material replaceMaterial, byte replaceData)
	{
		replace = new MaterialData(replaceMaterial, replaceData);
		replaceable.add(new MaterialData(targetMaterial, targetData));
	}
	
	public void addReplaceable(Material material)
	{
		replaceable.add(material);
	}

	public BlockList getBlocks()
	{
		return blocks;
	}

	public boolean perform(Block block)
	{
		if (replace == null) return false;
		
		if (replaceable == null || replaceable.contains(block.getType()))
		{
			block.setType(replace.getType());
			block.setData(replace.getData());
			return true;
		}
			
		return false;
	}

	public void setAffectedBlocks(BlockList blocks)
	{
		this.blocks = blocks;
	}
	
	protected MaterialData	replace;
	protected MaterialList	replaceable	= new MaterialList();
	protected BlockList		blocks		= null;
}
