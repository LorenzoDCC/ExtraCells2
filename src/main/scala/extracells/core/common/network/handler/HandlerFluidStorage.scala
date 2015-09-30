package extracells.core.common.network.handler

import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import cpw.mods.fml.relauncher.Side
import extracells.core.client.gui.GuiFluidStorage
import extracells.core.common.container.implementations.ContainerFluidStorage
import extracells.core.common.network.packet.PacketFluidStorage
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer

class HandlerFluidStorage extends IMessageHandler[PacketFluidStorage, IMessage] {
  override def onMessage(message: PacketFluidStorage, ctx: MessageContext): IMessage = {
    val player: EntityPlayer = ctx.side match  {
      case Side.SERVER => ctx.getServerHandler.playerEntity
      case Side.CLIENT => Minecraft.getMinecraft.thePlayer
    }

    //Side checking so packet modes only go one way.
    (ctx.side, message.getMode) match {
      case (Side.CLIENT, 0) => null
      case (Side.SERVER, 2) => null
      case _ => message.getMode match {
        case 0 => player.openContainer match {
          case container: ContainerFluidStorage =>
            container.forceFluidUpdate()
            container.doWork()
            null
        }
        case 1 => player.openContainer match {
          case container: ContainerFluidStorage => container
            .receiveSelectedFluid(message.getCurrentFluid)
            null
        }
        case 2 => Minecraft.getMinecraft.currentScreen match {
          case storage: GuiFluidStorage => storage
            .inventorySlots.asInstanceOf[ContainerFluidStorage]
            .updateFluidList(message.getFluidList)
            null
        }
      }
    }


  }
}
