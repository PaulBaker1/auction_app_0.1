import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TransactionForm from './TransactionForm';

const TransactionList = () => {
    const [transactions, setTransactions] = useState([]);
    const [editing, setEditing] = useState(false);
    const [currentTransaction, setCurrentTransaction] = useState(null);

    useEffect(() => {
        fetchTransactions();
    }, []);

    const fetchTransactions = async () => {
        const response = await axios.get('/api/transactions');
        setTransactions(response.data);
    };

    const handleEdit = (transaction) => {
        setEditing(true);
        setCurrentTransaction(transaction);
    };

    const handleDelete = async (id) => {
        await axios.delete(`/api/transactions/${id}`);
        fetchTransactions();
    };

    const handleFormSubmit = () => {
        fetchTransactions();
        setEditing(false);
        setCurrentTransaction(null);
    };

    return (
        <div>
            <h2>Transaction Records</h2>
            <TransactionForm
                editing={editing}
                currentTransaction={currentTransaction}
                onSubmit={handleFormSubmit}
            />
            <ul>
                {transactions.map(transaction => (
                    <li key={transaction.id}>
                        {transaction.description} - ${transaction.amount} - {transaction.timestamp}
                        <button onClick={() => handleEdit(transaction)}>Edit</button>
                        <button onClick={() => handleDelete(transaction.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TransactionList;
