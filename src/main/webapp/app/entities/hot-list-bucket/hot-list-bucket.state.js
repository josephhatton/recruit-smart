(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('hot-list-bucket', {
        parent: 'entity',
        url: '/hot-list-bucket',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HotListBuckets'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hot-list-bucket/hot-list-buckets.html',
            controller: 'HotListBucketController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('hot-list-bucket-detail', {
        parent: 'hot-list-bucket',
        url: '/hot-list-bucket/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HotListBucket'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hot-list-bucket/hot-list-bucket-detail.html',
            controller: 'HotListBucketDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'HotListBucket', function ($stateParams, HotListBucket) {
            return HotListBucket.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'hot-list-bucket',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('hot-list-bucket-detail.edit', {
        parent: 'hot-list-bucket-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hot-list-bucket/hot-list-bucket-dialog.html',
            controller: 'HotListBucketDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HotListBucket', function (HotListBucket) {
                return HotListBucket.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hot-list-bucket.new', {
        parent: 'hot-list-bucket',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hot-list-bucket/hot-list-bucket-dialog.html',
            controller: 'HotListBucketDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  userId: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('hot-list-bucket', null, {reload: 'hot-list-bucket'});
          }, function () {
            $state.go('hot-list-bucket');
          });
        }]
      })
      .state('hot-list-bucket.edit', {
        parent: 'hot-list-bucket',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hot-list-bucket/hot-list-bucket-dialog.html',
            controller: 'HotListBucketDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HotListBucket', function (HotListBucket) {
                return HotListBucket.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hot-list-bucket', null, {reload: 'hot-list-bucket'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hot-list-bucket.delete', {
        parent: 'hot-list-bucket',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hot-list-bucket/hot-list-bucket-delete-dialog.html',
            controller: 'HotListBucketDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['HotListBucket', function (HotListBucket) {
                return HotListBucket.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hot-list-bucket', null, {reload: 'hot-list-bucket'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
