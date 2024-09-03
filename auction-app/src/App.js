import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AuctionItemList from './components/AuctionItemList';
import AuctionItemDetail from './components/AuctionItemDetail';
import AuctionItemEdit from './components/AuctionItemEdit';
import PaymentForm from './components/PaymentForm';
import TransactionStatus from './components/TransactionStatus';
import { Container } from 'react-bootstrap';
import './App.css'; // Ensure you import your CSS

const App = () => {
    const [selectedItemId, setSelectedItemId] = useState(null);
    const [transactionId, setTransactionId] = useState('');
    const [paymentUrl, setPaymentUrl] = useState('');

    return (
        <Router>
            <Container>
                <h1 className="my-4">Auction App</h1>
                <div className="container">
                    <div className="sidebar">
                        <AuctionItemList onSelectItem={setSelectedItemId} />
                    </div>
                    <div className="main-content">
                        <Routes>
                            <Route path="/edit/:itemId" element={<AuctionItemEdit />} />
                            <Route path="/" element={<AuctionItemDetail itemId={selectedItemId} />} />
                            <Route path="/payment" element={<PaymentForm setTransactionId={setTransactionId} setPaymentUrl={setPaymentUrl} />} />
                            <Route path="/status" element={<TransactionStatus transactionId={transactionId} />} />
                        </Routes>
                        {paymentUrl && (
                            <div>
                                <p>Complete your payment at: <a href={paymentUrl} target="_blank" rel="noopener noreferrer">{paymentUrl}</a></p>
                            </div>
                        )}
                    </div>
                </div>
            </Container>
        </Router>
    );
};

export default App;
