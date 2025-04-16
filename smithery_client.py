import asyncio

import smithery
import mcp
from mcp.client.websocket import websocket_client

# Create Smithery URL with server endpoint
url = smithery.create_smithery_url("wss://server.smithery.ai/@if-of/mcp-delayed-counter/ws", {}) + "&api_key=REPLACE_ME"

async def main():
    # Connect to the server using websocket client
    async with websocket_client(url) as streams:
        async with mcp.ClientSession(*streams) as session:
            # List available tools
            tools_result = await session.list_tools()
            print(f"Available tools: {', '.join([t.name for t in tools_result.tools])}")
            
            # Example: Call a tool
            result = await session.call_tool("getFissoIndex", {})
            print(f"Tool result: {result}")

asyncio.run(main())