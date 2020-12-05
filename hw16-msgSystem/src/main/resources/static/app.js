let stompClient = null;

const form = document.querySelector('form');
const tbody = document.querySelector('tbody');

const getFormData = () => {
    const formData = new FormData(form);
    const jsonData = {};
    formData.forEach((value, key) => jsonData[key] = value);

    return JSON.stringify(jsonData);
}

const updateUsersTable = body => {
    const {id, name, address, phone} = JSON.parse(body);
    tbody.insertAdjacentHTML('beforeend', `
        <tr>
            <td>${id}</td>
            <td>${name}</td>
            <td>${address}</td>
            <td>${phone}</td>
        </tr>`);
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/response', ({body}) => updateUsersTable(body));
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

const createUser = evt => {
    evt.preventDefault();
    stompClient.send('/api/user', {}, getFormData());
};

form.addEventListener('submit', createUser);
window.addEventListener('load', connect);
window.addEventListener('beforeunload', disconnect);