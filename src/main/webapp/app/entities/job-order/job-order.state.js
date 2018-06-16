(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('job-order', {
        parent: 'entity',
        url: '/job-order?page&sort&search',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'JobOrders'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/job-order/job-order.html',
            controller: 'JobOrderController',
            controllerAs: 'vm'
          }
        },
        params: {
          page: {
            value: '1',
            squash: true
          },
          sort: {
            value: 'id,asc',
            squash: true
          },
          search: null
        },
        resolve: {
          pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
            return {
              page: PaginationUtil.parsePage($stateParams.page),
              sort: $stateParams.sort,
              predicate: PaginationUtil.parsePredicate($stateParams.sort),
              ascending: PaginationUtil.parseAscending($stateParams.sort),
              search: $stateParams.search
            };
          }],
        }
      })
      .state('job-order-detail', {
        parent: 'job-order',
        url: '/job-order/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'JobOrder'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/job-order/job-order-detail.html',
            controller: 'JobOrderDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'JobOrder', function ($stateParams, JobOrder) {
            return JobOrder.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'job-order',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('job-order-detail.edit', {
        parent: 'job-order-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order/job-order-dialog.html',
            controller: 'JobOrderDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['JobOrder', function (JobOrder) {
                return JobOrder.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('job-order.new', {
        parent: 'job-order',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order/job-order-dialog.html',
            controller: 'JobOrderDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  title: null,
                  duration: null,
                  description: null,
                  salary: null,
                  hourly: null,
                  jobType: null,
                  jobStatus: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('job-order', null, {reload: 'job-order'});
          }, function () {
            $state.go('job-order');
          });
        }]
      })
      .state('job-order.edit', {
        parent: 'job-order',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order/job-order-dialog.html',
            controller: 'JobOrderDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['JobOrder', function (JobOrder) {
                return JobOrder.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('job-order', null, {reload: 'job-order'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('job-order-detail.hot-list', {
        parent: 'job-order-detail',
        url: '/detail/hot/list/{id}',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order/job-order-hot-list-modal.html',
            controller: 'JobOrderHotListModalController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['JobOrder', function (JobOrder) {
                return JobOrder.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('job-order', null, {reload: 'job-order'});
          }, function () {
            $state.go('^');
          });
        }]
      })

      .state('job-order.delete', {
        parent: 'job-order',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order/job-order-delete-dialog.html',
            controller: 'JobOrderDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['JobOrder', function (JobOrder) {
                return JobOrder.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('job-order', null, {reload: 'job-order'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
