const WebSocket = require("ws");
const ws = new WebSocket("ws://localhost:4001/hr/api/v1/events");
ws.on("message", frame_data => {
    const frame = JSON.parse(frame_data);
    console.log(frame);
})