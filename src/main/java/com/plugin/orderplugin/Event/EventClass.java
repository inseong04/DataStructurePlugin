package com.plugin.orderplugin.Event;

import com.plugin.orderplugin.OrderPlugin;
import com.plugin.orderplugin.command.MerchantCommand;
import com.plugin.orderplugin.model.ClientRequestModel;
import com.plugin.orderplugin.model.OrderModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.plugin.orderplugin.OrderPlugin.requestList;
import static com.plugin.orderplugin.command.CustomerCommand.merchantName;

public class EventClass implements Listener {

    int bread = 0;
    int milk = 0;
    int water = 0;
    int chicken = 0;
    int fish = 0;

    @EventHandler
    public void openInventory(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        OrderModel orderModel = new OrderModel(player.getName());
        if(e.getView().getTitle().equals("주문 메뉴")){
            e.setCancelled(true);
            switch (e.getRawSlot()){
                case 11->{
                    bread+=1;
                    orderModel.setBread(bread);
                    player.sendMessage(orderModel.bread+"");
                }
                case 12->{
                    milk+=1;
                    orderModel.setMilk(milk);
                }
                case 13->{
                    water+=1;
                    orderModel.setMilk(water);
                }
                case 14->{
                    chicken+=1;
                    orderModel.setChicken(chicken);
                }
                case 15->{
                    fish+=1;
                    orderModel.setFish(fish);
                }
                case 18->{
                    e.getView().close();
                }
                case 26->{
                    e.getView().close();
                    //requestList.add(new ClientRequestModel(player.getDisplayName(), merchantName, orderModel));
                    player.sendMessage(merchantName+"님에게"+" 주문을 보냈습니다. 기다려주세요");
                    Player targetPlayer = player.getServer().getPlayer(merchantName);

                    String orderStr = "빵:"+orderModel.milk+"개/우유:"+orderModel.milk+"개/물:"+orderModel.water+"개/치킨:"+orderModel.chicken+"개/생선"+orderModel.fish+"개";
                    targetPlayer.sendMessage("[상점] "+player.getName()+" 님이 "+orderStr+ "주문을 요청하였습니다." +
                        "\n"+"요청을 수락하시려면 /상점 수락" +
                            "\n"+"요청을 거절하시려면 /상점 거절");
                }
            }
        }
    }

    @EventHandler
    public void onChatReceive(AsyncPlayerChatEvent e) {
        String message = e.getMessage();

        if (message != null) {
            String[] splitMessage = message.split(" ");

            if (splitMessage[0].equals("[상점]")) {
                MerchantCommand.clientName = splitMessage[1];
                MerchantCommand.requestItem = splitMessage[3];
            }
        }
    }
}
