package bat.ke.qq.com;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 源码学院-ant
 * 只为培养BAT程序员而生
 * http://bat.ke.qq.com
 * 往期视频加群:516212256 暗号:6
 */
//@ChannelHandler.Sharable/*该handler能在多个线程间共享，那么实现必须是线程安全的*/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String result="";
        //接收到完成的http请求
        FullHttpRequest request=(FullHttpRequest)msg;
        try{
            String path = request.uri();
            HttpMethod method = request.method();
            if(!"/bat".equalsIgnoreCase(path)){
                result = "非法请求："+path;
                send(result,ctx,HttpResponseStatus.BAD_REQUEST);
                return;
            }

            if(HttpMethod.GET.equals(method)){
                result=ResponseUtils.getResult();
                send(result,ctx,HttpResponseStatus.OK);
            }
        }catch(Exception e){
            System.out.println("处理请求失败!");
            e.printStackTrace();
        }finally{
            request.release();
        }
    }

    private void send(String content, ChannelHandlerContext ctx,
                      HttpResponseStatus status){
        FullHttpResponse response =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                        Unpooled.copiedBuffer(content,CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,
                "text/plain;charset=UTF-8");

        ctx.writeAndFlush(response)
                .addListener(ChannelFutureListener.CLOSE);

    }
}
