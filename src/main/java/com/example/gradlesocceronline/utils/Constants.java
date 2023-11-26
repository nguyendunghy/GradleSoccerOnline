package com.example.gradlesocceronline.utils;


public class Constants {
    private Constants() {
    }


    public static final class REGISTER {
        public static final String USER_PASS_EMPTY = "User name or password empty";
        public static final String CONFIRM_PASSWORD_NOT_MATCH = "Password and confirm password not matched";
        public static final String USER_EXISTED = "User existed";
        public static final String WEAK_PASSWORD = "Weak password. Password must have digit, lower case, uppercase letter and 8 to 20 long";

        public static final String INVALID_USER_NAME = "Invalid username. Username must be an email";

    }

    public static final class LOGIN {
        public static final String SUCCESS = "SUCCESS";
        public static final String INVALID_USER_PASS = "Invalid user or password";
        public static final String USER_PASS_EMPTY = "User name or password empty";

        public static final String PASSWORD_NOT_MATCH = "Password and confirmed password not match";

    }

    public static final class MESSAGE {
        public static final String SUCCESS = "SUCCESS";

        public static final String INTERNAL_ERROR = "Internal error";

        public static final String BAD_REQUEST = "Bad request";

        public static final String NO_PERMISSION_EDIT_TEAM = "No permission editing team";

        public static final String NO_PERMISSION_EDIT_PLAYER = "No permission editing player";


        public static final String TEAM_NOT_FOUND = "Team not found";

        public static final String PLAYER_NOT_FOUND = "Player not found";

        public static final String INVALID_USER = "Invalid user";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String INVALID_USER_PASS = "Invalid user or password";

        public static final String PLAYER_ID_REQUIRED = "Player id required";
        public static final String NO_PERMISSION_TRANSFER_PLAYER = "No permission transferring player";
        public static final String PRICE_REQUIRED = "Price required";
        public static final String INVALID_PRICE = "Invalid price";
        public static final String TRANSFER_PLAYER_EXISTED = "Transfer player existed";
        public static final String TRANSFER_PLAYER_NOT_FOUND = "Transfer player not found";
        public static final String NOT_ENOUGH_BUDGET = "Not enough budget";
        public static final String TO_TEAM_NOT_FOUND = "Target team not found";
        public static final String FROM_TEAM_NOT_FOUND = "Source team not found";
        public static final String TRANSFER_TEAM_DUPLICATE = "Target team must be different from source team";
        public static final String TOKEN_NOT_FOUND = "Token not found";
        public static final String INVALID_TOKEN = "Invalid token";
    }

    public static final String INTERNAL_ERROR = "404";

    public static final class CODE {

        public static final String SUCCESS = "200";

        public static final class ERROR {
            public static final String INTERNAL_ERROR = "404";

            public static final String NO_PERMISSION_EDIT_TEAM = "10000";
            public static final String EDIT_TEAM = "10001";

            public static final String EDIT_PLAYER = "10002";


            public static final String NO_PERMISSION_EDIT_PLAYER = "10003";


            public static final String AUTHORIZATION_FAIL = "10004";
            public static final String USER_NOT_FOUND = "10005";
            public static final String PASSWORD_NOT_MATCH = "10006";
            public static final String ADD_TRANSFER_PLAYER = "10007";
            public static final String TRANSFER_PLAYER_NOT_FOUND = "10008";
            public static final String PLAYER_NOT_FOUND = "10009";
            public static final String TRANSFER_PLAYER = "10010" ;
            public static final String LOGOUT = "10011";
        }
    }


    public static final long EXPIRED_TIME = 60 * 60 * 1000;

    public static final Long ACTIVE = 1L;
    public static final Long IN_ACTIVE = 0L;

    public static final Long TOKEN_TTL = 60L; //min

    public static final class REQUEST_ATTRIBUTE {
        public static final String USER_ID = "user_id";
        public static final String TEAM_ID = "team_id";

    }


    public static final Long INIT_TEAM_BUDGET = 5 * 1000 * 1000L;
    public static final Long INIT_TEAM_VALUE = 20 * 1000 * 1000L;
    public static final Long INIT_PLAY_VALUE = 1000 * 1000L;

}
