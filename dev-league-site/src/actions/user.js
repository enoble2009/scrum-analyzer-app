import config from '../config';

const axios = require('axios').default;

export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
export const LOGOUT_REQUEST = 'LOGOUT_REQUEST';
export const LOGOUT_SUCCESS = 'LOGOUT_SUCCESS';

export function receiveLogin() {
    return {
        type: LOGIN_SUCCESS
    };
}

function loginError(payload) {
    return {
        type: LOGIN_FAILURE,
        payload,
    };
}

function requestLogout() {
    return {
        type: LOGOUT_REQUEST,
    };
}

export function receiveLogout() {
    return {
        type: LOGOUT_SUCCESS,
    };
}

// Logs the user out
export function logoutUser() {
    return (dispatch) => {
        dispatch(requestLogout());
        localStorage.removeItem('authenticated');
        localStorage.removeItem('loggedUser');
        dispatch(receiveLogout());
    };
}

export function loginUser(creds) {
    return (dispatch) => {

        if (creds.email.length > 0 && creds.password.length > 0) {
            axios.post(config.baseUrl.api+'login?user='+creds.email+'&password='+creds.password, undefined, {
                headers: { 'Authorization': undefined }
              })
                .then(function (response) {
                    localStorage.setItem('authenticated', true);
                    localStorage.setItem('token', response.data.token);
                    dispatch(receiveLogin());
                })
                .catch(function (error) {
                    dispatch(loginError('Something was wrong. Try again'));
                });
        } else {
            dispatch(loginError('Something was wrong. Try again'));
        }
    }
}
