INSERT INTO users (id, name, capacity)
VALUES (1, 'mock-user', 8),
       (2, 'test-user-1', 4),
       (3, 'test-user-2', 6);

INSERT INTO projects (id, name, owner_id)
VALUES (1, 'Personal Productivity', 1);

INSERT INTO tasks (id, title, priority, estimated_duration, deadline, project_id, assigned_user_id)
VALUES (1, 'Write documentation', 'HIGH', 2, TIMESTAMP '2025-05-09 10:00:00', 1, 1),
       (2, 'Fix bugs', 'MEDIUM', 1, TIMESTAMP '2025-05-08 16:00:00', 1, 1),
       (3, 'Prepare slides', 'HIGH', 3, TIMESTAMP '2025-05-10 14:00:00', 1, 1),
       (4, 'Code review', 'LOW', 1, TIMESTAMP '2025-05-07 18:00:00', 1, 1),
       (5, 'Team meeting', 'MEDIUM', 2, TIMESTAMP '2025-05-08 09:00:00', 1, 1);