package com.elmakers.mine.bukkit.gameplay;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.elmakers.mine.bukkit.gameplay.dao.BlockData;
import com.elmakers.mine.bukkit.gameplay.dao.BlockList;

public class BlockRecurse
{
	
	public int recurse(Block startBlock, BlockRecurseAction recurseAction)
	{
		BlockList affectedBlocks = new BlockList();
		recurse(startBlock, recurseAction, affectedBlocks, null, 0);
		recurseAction.setAffectedBlocks(affectedBlocks);
		return affectedBlocks.size();
	}
	
	protected void recurse(Block block, BlockRecurseAction recurseAction, BlockList affectedBlocks, BlockFace nextFace, int rDepth)
	{
		if (nextFace != null)
		{
			block = block.getFace(nextFace);
		}
		if (affectedBlocks.contains(block))
		{
			return;
		}
		affectedBlocks.add(block);			
		
		if (!recurseAction.perform(block))
		{
			return;
		}
		
		if (rDepth < maxRecursion)
		{
			for (BlockFace face : BlockData.FACES)
			{
				if (nextFace == null || nextFace != BlockData.getReverseFace(face))
				{
					recurse(block, recurseAction, affectedBlocks, face, rDepth + 1);
				}
			}
		}
	}
	
	protected int maxRecursion = 8;
}
