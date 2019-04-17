# Android - Broadcast Sender and Receiver
## Learning Objective - Broadcast Receivers
### App Name - BcSender, BcReceiver

BcSender takes some dollar amount and target currency as input. It then sends a broadcast containing the amount and currency.

BcReceiver launchs by itself when BcSender broadcasts. It intercepts the broadcast, takes the amount and target currency, converts it, and sends a reply broadcast containing the converted amount.

Upon successful conversion, the main activity (BcSender) displays note of conversion.
