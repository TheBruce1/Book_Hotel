import axios from "axios";

export default class ApiService {

    static BASE_URL = "http://localhost:1001"

    static getHeader() {
        const token = localStorage.getItem("token");
        return {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        };
    }

    /**AUTH */

    /**Register a new user */
    static async registerUser(registration) {
        const response = await axios.post(`${this.BASE_URL}/auth/register`, registration)
        return response.data
    }

    /**Login a registered user */
    static async loginUser(loginDetails) {
        const response = await axios.post(`${this.BASE_URL}/auth/login`, loginDetails)
        return response.data
    }

    /**USERS */

    /**Get all users */
    static async getAllUsers() {
        const response = await axios.get(`${this.BASE_URL}/users/all`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**Get user profile */
    static async getUserProfile() {
        const response = await axios.get(`${this.BASE_URL}/users/get-logged-in-profile-info`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**Get user by userId */
    static async getUser() {
        const response = await axios.get(`${this.BASE_URL}/users/get-by-id/${userId}`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**Get user bookings by userId */
    static async getUserBookings() {
        const response = await axios.get(`${this.BASE_URL}/users/get-user-bookings/${userId}`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**Delete user by userId */
    static async deleteUser() {
        const response = await axios.delete(`${this.BASE_URL}/users/delete/${userId}`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**ROOMS */

    /**Add a new room */

    static async addRoom(formData) {
        const response = await axios.post(`${this.BASE_URL}/rooms/add`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        })
        return response.data
    }

    /**get all available rooms */
    static async getAllAvailableRooms() {
        const response = await axios.get(`${this.BASE_URL}/rooms/all-available-roooms`)
        return response.data
    }

    /**Get all available room by date and room type */
    static async getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType) {
        const response = await axios.get(`${this.BASE_URL}/rooms/available-rooms-by-date-and-type?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${roomType}`)
        return response.data
    }

    /**Get all room types */
    static async getRoomTypes() {
        const response = await axios.get(`${this.BASE_URL}/rooms/types`)
        return response.data
    }

    /**Get all rooms */
    static async getAllRooms() {
        const response = await axios.get(`${this.BASE_URL}/rooms/all`)
        return response.data
    }

    /**Get room by roomId */
    static async getRoomById(roomId) {
        const response = await axios.get(`${this.BASE_URL}/rooms/room-by-id/${roomId}`)
        return response.data
    }

    /**Delete room by roomId */
    static async deleteRoom(roomId) {
        const response = await axios.delete(`${this.BASE_URL}/rooms/delete/${roomId}`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**Update a room */
    static async updateRoom(roomId, formData) {
        const response = await axios.put(`${this.BASE_URL}/rooms/update/${roomId}`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        })
        return response.data
    }

    /**BOOKING */

    /**Book a room */
    static async bookRoom(roomId, userId, booking) {
        console.log("USER ID IS : " + userId)
        const response = await axios.post(`${this.BASE_URL}/bookings/book-room/${roomId}/${userId}`, booking, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**Get all bookings */
    static async getAllBookings() {
        const response = await axios.get(`${this.BASE_URL}/bookings/all`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**Get booking by confirmation code */
    static async getBookingByConfirmationCode(bookingCode) {
        const response = await axios.get(`${this.BASE_URL}/bookings/get-by-confirmation-code/${bookingCode}`)
        return response.data
    }

    /**Cancel booking by bookingId*/
    static async cancelBooking(bookingId) {
        const response = await axios.delete(`${this.BASE_URL}/bookings/cancel/${bookingId}`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**AUTHENTICATION CHECKER */
    static logout() {
        localStorage.removeItem('token')
        localStorage.removeItem('role')
    }

    static isAuthenticated() {
        const token = localStorage.getItem('token')
        return !!token
    }

    static isAdmin() {
        const role = localStorage.getItem('role')
        return role === 'ADMIN'
    }

    static isUser() {
        const role = localStorage.getItem('role')
        return role === 'USER'
    }
}